import { ApiProperty } from '@nestjs/swagger';

export class CreateUserDto {
  @ApiProperty({ description: 'Name of the user', example: 'testuser' })
  name: string;

  @ApiProperty({ description: 'Email address of the user', example: 'test.user@example.com' })
  email: string;

  @ApiProperty({ description: 'Password for the user account', example: 'password' })
  password: string;
}