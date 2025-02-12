import { Injectable } from '@nestjs/common';
import { PrismaService } from '../prisma.service';
import { CreateSaveFileDto } from './dto/create-save_file.dto';
import { UpdateSaveFileDto } from './dto/update-save_file.dto';

@Injectable()
export class SaveFilesService {
  constructor(private prisma: PrismaService) {}

  create(createSaveFileDto: CreateSaveFileDto) {
    return this.prisma.save_files.create({ data: createSaveFileDto });
  }

  findAll() {
    return this.prisma.save_files.findMany();
  }

  findOne(id: number) {
    return this.prisma.save_files.findUnique({ where: { id } });
  }

  update(id: number, updateSaveFileDto: UpdateSaveFileDto) {
    return this.prisma.save_files.update({ where: { id }, data: updateSaveFileDto });
  }

  remove(id: number) {
    return this.prisma.save_files.delete({ where: { id } });
  }
}