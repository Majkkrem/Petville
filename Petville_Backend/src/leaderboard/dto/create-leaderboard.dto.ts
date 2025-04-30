import { ApiProperty } from '@nestjs/swagger';

export class CreateLeaderboardDto {
  @ApiProperty({ description: 'ID of the player', example: 1 })
  user_id: number;
  

  @ApiProperty({ description: 'Score of the player', example: 100 })
  score: number;

  @ApiProperty({ description: 'ID of the save file', example: 1 })
  save_file_id: number;
}