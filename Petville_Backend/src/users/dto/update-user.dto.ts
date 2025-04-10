import { PartialType } from '@nestjs/mapped-types';
import { CreateUserDto } from './create-user.dto';
import { ApiProperty } from '@nestjs/swagger';

export class UpdateUserDto extends PartialType(CreateUserDto) {
  @ApiProperty({ description: 'Updated name of the user', example: 'Test User', required: false })
  name?: string;

  @ApiProperty({ description: 'Updated email address of the user', example: 'Password', required: false })
  email?: string;

  @ApiProperty({ description: 'Updated password for the user account', example: 'Password', required: false })
  password?: string;
}