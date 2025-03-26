import { Injectable } from '@nestjs/common';
import { LoginDto } from './dto/login.dto';
import { verify } from 'argon2';
import { JwtService } from '@nestjs/jwt';
import { PrismaService } from 'src/prisma.service';

@Injectable()
export class AuthService {
  constructor(private db: PrismaService, private jwtService: JwtService) { }

  async login(loginDto: LoginDto): Promise<{ sub: number, name: string, role: string }> {
    const user = await this.db.users.findUniqueOrThrow({
      where: {
        name: loginDto.name
      }
    });
    if (await verify(user.password, loginDto.password)) {
      const payload = { sub: user.id, name: user.name, role: user.role };
      return payload;
    } else {
      throw new Error('Invalid pass');
    }
  }

  async generateTokens(payload: any): Promise<{ access_token: string, refresh_token: string }> {
    const access_token = await this.jwtService.signAsync(payload);
    const refresh_token = await this.jwtService.signAsync(payload);
    return { access_token, refresh_token };
  }

  async validateToken(token: string): Promise<any> {
    return this.jwtService.verifyAsync(token);
  }
}
