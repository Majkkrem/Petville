import { Module } from '@nestjs/common';
import { LoadService } from './load.service';
import { LoadController } from './load.controller';

@Module({
  controllers: [LoadController],
  providers: [LoadService],
})
export class LoadModule {}
