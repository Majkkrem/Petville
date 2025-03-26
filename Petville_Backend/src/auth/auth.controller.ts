import { Body, Controller, Post, Req, Res, UnauthorizedException, UseGuards } from '@nestjs/common';
import { AuthService } from './auth.service';
import { LoginDto } from './dto/login.dto';
import { ApiBody, ApiHeader, ApiOperation, ApiResponse, ApiUnauthorizedResponse } from '@nestjs/swagger';
import { Response, Request } from 'express';
import { JwtAuthGuard } from './guards/jwt-auth.guard';
import { Roles } from './roles.decorator';
import { Role } from '@prisma/client';
import { RolesGuard } from './guards/roles.guard';
import { log } from 'console';

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Post('login')
  @ApiOperation({ summary: 'User login' })
  @ApiBody({ type: LoginDto })
  @ApiResponse({ status: 200, description: 'Logged in successfully', type: Object })
  @ApiUnauthorizedResponse({ description: 'Invalid email or password' })
  @ApiHeader({
    name: 'Cookie',
    description: 'Stores access_token and refresh_token in cookies',
  })
  async login(@Body() loginDto: LoginDto, @Res() res: Response) {
    const { name, password } = loginDto;   
    let payload;
    try {
      payload = await this.authService.login(loginDto);
    } catch (e) {
      throw new UnauthorizedException('Invalid email or password');
    }
    const { access_token, refresh_token } = await this.authService.generateTokens(payload);

    res.cookie('access_token', access_token, { httpOnly: true });
    res.cookie('refresh_token', refresh_token, { httpOnly: true });
    return res.send({ id: payload.sub });
  }

  @Post('logout')
  @ApiOperation({ summary: 'User logout' })
  @ApiResponse({ status: 200, description: 'Logged out successfully', type: Object })
  @ApiUnauthorizedResponse({ description: 'No access token' })
  @ApiUnauthorizedResponse({ description: 'No refresh token' })
  @ApiHeader({
    name: 'Cookie',
    description: 'Must include access_token and refresh_token cookies',
  })
  @Roles(Role.ADMIN, Role.USER)
  @UseGuards(JwtAuthGuard, RolesGuard)
  async logout(@Res() res: Response) {
    res.clearCookie('access_token');
    res.clearCookie('refresh_token');
    return res.send({ message: 'Logged out successfully' });
  }

  @Post('refresh')
  @ApiOperation({ summary: 'Refresh access token using refresh token cookie' })
  @ApiResponse({ status: 200, description: 'Token refreshed successfully' })
  @ApiResponse({ status: 401, description: 'Invalid refresh token' })
  @ApiUnauthorizedResponse({ description: 'No refresh token' })
  @ApiHeader({
    name: 'Cookie',
    description: 'Must include refresh_token cookie',
  })
  async refresh(@Req() req: Request, @Res() res: Response) {
    const refresh_token = req.cookies?.refresh_token;
    if (!refresh_token) {
      throw new UnauthorizedException('No refresh token');
    }

    let payload;
    try {
      payload = await this.authService.validateToken(refresh_token);
    } catch (e) {
      throw new UnauthorizedException('Invalid refresh token');
    }
    delete payload.iat;
    delete payload.exp;

    const { access_token, refresh_token: new_refresh_token } = await this.authService.generateTokens(payload);
    res.cookie('access_token', access_token, { httpOnly: true });
    res.cookie('refresh_token', new_refresh_token, { httpOnly: true });
    return res.send({ message: 'Tokens refreshed' });
  }
}
