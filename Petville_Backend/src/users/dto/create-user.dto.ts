import { ApiProperty } from '@nestjs/swagger';
import { IsEmail, IsString, IsStrongPassword } from 'class-validator';

export class CreateUserDto {
  @ApiProperty({ description: 'Name of the user', example: 'testuser' })
  @IsString()
  name: string;

  @ApiProperty({ description: 'Email address of the user', example: 'test.user@example.com' })
  @IsEmail()
  email: string;

  @ApiProperty({ description: 'Password for the user account', example: 'password' })
  password: string;
}