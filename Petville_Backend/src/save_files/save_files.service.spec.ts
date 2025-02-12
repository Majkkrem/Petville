import { Test, TestingModule } from '@nestjs/testing';
import { SaveFilesService } from './save_files.service';

describe('SaveFilesService', () => {
  let service: SaveFilesService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [SaveFilesService],
    }).compile();

    service = module.get<SaveFilesService>(SaveFilesService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
