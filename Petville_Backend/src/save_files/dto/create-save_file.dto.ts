import { ApiProperty } from '@nestjs/swagger';

export class CreateSaveFileDto {
  @ApiProperty({ description: 'ID of the user', example: 1 })
  user_id: number;

  @ApiProperty({ description: 'Name of the pet', example: 'DoggyAndy' })
  petName: string;

  @ApiProperty({ description: 'Type of the pet', example: 'Dog' })
  petType: string;

  @ApiProperty({ description: 'Energy level of the pet', example: 80 })
  petEnergy: number;

  @ApiProperty({ description: 'Hunger level of the pet', example: 40 })
  petHunger: number;

  @ApiProperty({ description: 'Mood level of the pet', example: 90 })
  petMood: number;

  @ApiProperty({ description: 'Health level of the pet', example: 100 })
  petHealth: number;

  @ApiProperty({ description: 'Total hours the player has played', example: 120 })
  hoursPlayer: number;

  @ApiProperty({ description: 'Total gold earned by the player', example: 5000 })
  goldEarned: number;

  @ApiProperty({ description: 'Current gold the player has', example: 1500 })
  currentGold: number;
}