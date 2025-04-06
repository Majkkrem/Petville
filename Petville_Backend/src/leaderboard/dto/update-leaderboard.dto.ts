import { PartialType } from '@nestjs/mapped-types';
import { CreateLeaderboardDto } from './create-leaderboard.dto';
import { ApiProperty } from '@nestjs/swagger';

export class UpdateLeaderboardDto extends PartialType(CreateLeaderboardDto) {
  @ApiProperty({ description: 'Updated score of the player', example: 1500, required: false })
  score?: number;
}