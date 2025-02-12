import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { SaveFilesService } from './save_files.service';
import { CreateSaveFileDto } from './dto/create-save_file.dto';
import { UpdateSaveFileDto } from './dto/update-save_file.dto';

@Controller('save-files')
export class SaveFilesController {
  constructor(private readonly saveFilesService: SaveFilesService) {}

  @Post()
  create(@Body() createSaveFileDto: CreateSaveFileDto) {
    return this.saveFilesService.create(createSaveFileDto);
  }

  @Get()
  findAll() {
    return this.saveFilesService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.saveFilesService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateSaveFileDto: UpdateSaveFileDto) {
    return this.saveFilesService.update(+id, updateSaveFileDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.saveFilesService.remove(+id);
  }
}
