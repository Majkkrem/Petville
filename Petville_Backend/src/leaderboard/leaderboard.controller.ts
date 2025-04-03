import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiParam, ApiBody, ApiResponse } from '@nestjs/swagger';
import { LeaderboardService } from './leaderboard.service';
import { CreateLeaderboardDto } from './dto/create-leaderboard.dto';
import { UpdateLeaderboardDto } from './dto/update-leaderboard.dto';

@ApiTags('Leaderboard')
@Controller('leaderboard')
export class LeaderboardController {
  constructor(private readonly leaderboardService: LeaderboardService) {}

  @Post()
  @ApiOperation({ summary: 'Create a new leaderboard entry' })
  @ApiBody({ type: CreateLeaderboardDto })
  @ApiResponse({ status: 201, description: 'Leaderboard entry created successfully.' })
  create(@Body() createLeaderboardDto: CreateLeaderboardDto) {
    return this.leaderboardService.create(createLeaderboardDto);
  }

  @Get()
  @ApiOperation({ summary: 'Retrieve all leaderboard entries' })
  @ApiResponse({ status: 200, description: 'List of leaderboard entries retrieved successfully.' })
  findAll() {
    return this.leaderboardService.findAll();
  }

  @Get(':id')
  @ApiOperation({ summary: 'Retrieve a specific leaderboard entry by ID' })
  @ApiParam({ name: 'id', description: 'ID of the leaderboard entry' })
  @ApiResponse({ status: 200, description: 'Leaderboard entry retrieved successfully.' })
  @ApiResponse({ status: 404, description: 'Leaderboard entry not found.' })
  findOne(@Param('id') id: string) {
    return this.leaderboardService.findOne(+id);
  }

  @Patch(':id')
  @ApiOperation({ summary: 'Update a specific leaderboard entry by ID' })
  @ApiParam({ name: 'id', description: 'ID of the leaderboard entry' })
  @ApiBody({ type: UpdateLeaderboardDto })
  @ApiResponse({ status: 200, description: 'Leaderboard entry updated successfully.' })
  @ApiResponse({ status: 404, description: 'Leaderboard entry not found.' })
  update(@Param('id') id: string, @Body() updateLeaderboardDto: UpdateLeaderboardDto) {
    return this.leaderboardService.update(+id, updateLeaderboardDto);
  }

  @Delete(':id')
  @ApiOperation({ summary: 'Delete a specific leaderboard entry by ID' })
  @ApiParam({ name: 'id', description: 'ID of the leaderboard entry' })
  @ApiResponse({ status: 200, description: 'Leaderboard entry deleted successfully.' })
  @ApiResponse({ status: 404, description: 'Leaderboard entry not found.' })
  remove(@Param('id') id: string) {
    return this.leaderboardService.remove(+id);
  }
}