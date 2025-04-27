import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { LoadService } from './load.service';
import { CreateLoadDto } from './dto/create-load.dto';
import { UpdateLoadDto } from './dto/update-load.dto';

@Controller('load')
export class LoadController {
  constructor(private readonly loadService: LoadService) {}

  @Post()
  create(@Body() createLoadDto: CreateLoadDto) {
    return this.loadService.create(createLoadDto);
  }

  @Get()
  findAll() {
    return this.loadService.findAll();
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.loadService.findOne(+id);
  }

  @Patch(':id')
  update(@Param('id') id: string, @Body() updateLoadDto: UpdateLoadDto) {
    return this.loadService.update(+id, updateLoadDto);
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.loadService.remove(+id);
  }
}
