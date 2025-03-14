import { Module } from '@nestjs/common';
import { SaveFilesService } from './save_files.service';
import { SaveFilesController } from './save_files.controller';
import { PrismaService } from 'src/prisma.service';

@Module({
  controllers: [SaveFilesController],
  providers: [SaveFilesService, PrismaService],
})
export class SaveFilesModule {}
