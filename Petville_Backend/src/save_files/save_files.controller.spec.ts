import { Test, TestingModule } from '@nestjs/testing';
import { SaveFilesController } from './save_files.controller';
import { SaveFilesService } from './save_files.service';

describe('SaveFilesController', () => {
  let controller: SaveFilesController;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [SaveFilesController],
      providers: [SaveFilesService],
    }).compile();

    controller = module.get<SaveFilesController>(SaveFilesController);
  });

  it('should be defined', () => {
    expect(controller).toBeDefined();
  });
});
