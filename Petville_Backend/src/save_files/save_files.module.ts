import { Module } from '@nestjs/common';
import { SaveFilesService } from './save_files.service';
import { SaveFilesController } from './save_files.controller';

@Module({
  controllers: [SaveFilesController],
  providers: [SaveFilesService],
})
export class SaveFilesModule {}
