import { Controller, Get, Post, Body, Patch, Param, Delete, UseGuards } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiParam, ApiBody, ApiResponse } from '@nestjs/swagger';
import { SaveFilesService } from './save_files.service';
import { CreateSaveFileDto } from './dto/create-save_file.dto';
import { UpdateSaveFileDto } from './dto/update-save_file.dto';
import { Role } from '@prisma/client';
import { JwtAuthGuard } from 'src/auth/guards/jwt-auth.guard';
import { RolesGuard } from 'src/auth/guards/roles.guard';
import { Roles } from 'src/auth/roles.decorator';

@ApiTags('Save Files')
@Controller('save-files')
export class SaveFilesController {
  constructor(private readonly saveFilesService: SaveFilesService) {}

  @Post()
  @ApiOperation({ summary: 'Create a new save file' })
  @ApiBody({ type: CreateSaveFileDto })
  @ApiResponse({ status: 201, description: 'Save file created successfully.' })
  create(@Body() createSaveFileDto: CreateSaveFileDto) {
    return this.saveFilesService.create(createSaveFileDto);
  }

  @Get()
  @ApiOperation({ summary: 'Retrieve all save files' })
  @ApiResponse({ status: 200, description: 'List of save files retrieved successfully.' })
  findAll() {
    return this.saveFilesService.findAll();
  }

  @Get(':id')
  @Roles(Role.ADMIN, Role.USER)
  @UseGuards(JwtAuthGuard, RolesGuard)
  findAllById(@Param('id') id: string) {
    return this.saveFilesService.findAllById(+id);
  }

  @Get(':id')
  @ApiOperation({ summary: 'Retrieve a specific save file by ID' })
  @ApiParam({ name: 'id', description: 'ID of the save file' })
  @ApiResponse({ status: 200, description: 'Save file retrieved successfully.' })
  @ApiResponse({ status: 404, description: 'Save file not found.' })
  findOne(@Param('id') id: string) {
    return this.saveFilesService.findOne(+id);
  }

  @Patch(':id')
  @ApiOperation({ summary: 'Update a specific save file by ID' })
  @ApiParam({ name: 'id', description: 'ID of the save file' })
  @ApiBody({ type: UpdateSaveFileDto })
  @ApiResponse({ status: 200, description: 'Save file updated successfully.' })
  @ApiResponse({ status: 404, description: 'Save file not found.' })
  update(@Param('id') id: string, @Body() updateSaveFileDto: UpdateSaveFileDto) {
    return this.saveFilesService.update(+id, updateSaveFileDto);
  }

  @Delete(':id')
  @ApiOperation({ summary: 'Delete a specific save file by ID' })
  @ApiParam({ name: 'id', description: 'ID of the save file' })
  @ApiResponse({ status: 200, description: 'Save file deleted successfully.' })
  @ApiResponse({ status: 404, description: 'Save file not found.' })
  remove(@Param('id') id: string) {
    return this.saveFilesService.remove(+id);
  }
}