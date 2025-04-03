import { PartialType } from '@nestjs/mapped-types';
import { CreateSaveFileDto } from './create-save_file.dto';
import { ApiProperty } from '@nestjs/swagger';

export class UpdateSaveFileDto extends PartialType(CreateSaveFileDto) {
  @ApiProperty({ description: 'ID of the user', example: 1, required: false })
  user_id?: number;

  @ApiProperty({ description: 'Name of the pet', example: 'Buddy', required: false })
  petName?: string;

  @ApiProperty({ description: 'Type of the pet', example: 'Dog', required: false })
  petType?: string;

  @ApiProperty({ description: 'Energy level of the pet', example: 80, required: false })
  petEnergy?: number;

  @ApiProperty({ description: 'Hunger level of the pet', example: 40, required: false })
  petHunger?: number;

  @ApiProperty({ description: 'Mood level of the pet', example: 90, required: false })
  petMood?: number;

  @ApiProperty({ description: 'Health level of the pet', example: 100, required: false })
  petHealth?: number;

  @ApiProperty({ description: 'Total hours the player has played', example: 120, required: false })
  hoursPlayer?: number;

  @ApiProperty({ description: 'Total gold earned by the player', example: 5000, required: false })
  goldEarned?: number;

  @ApiProperty({ description: 'Current gold the player has', example: 1500, required: false })
  currentGold?: number;
}