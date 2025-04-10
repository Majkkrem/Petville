import { ApiProperty } from "@nestjs/swagger";
import { IsString } from "class-validator";

export class LoginDto {
  @ApiProperty({
          example: "John Doe",
          description: "User's name",
          format: "name",
        })
  @IsString()
  name: string;
  
  @ApiProperty({
    example: "P@ssw0rd123!",
    description: "User's password (must be strong)",
    format: "password",
  })
  @IsString()
  password: string;
}

export class LoginDtoWeb {
  @ApiProperty({
          format: "email",
        })
  @IsString()
  email: string;
  
  @ApiProperty({
    example: "P@ssw0rd123!",
    description: "User's password (must be strong)",
    format: "password",
  })
  @IsString()
  password: string;
}
