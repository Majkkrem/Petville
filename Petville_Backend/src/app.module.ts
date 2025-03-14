import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UsersModule } from './users/users.module';
import { LeaderboardModule } from './leaderboard/leaderboard.module';
import { SaveFilesModule } from './save_files/save_files.module';
import { PrismaService } from './prisma.service';

@Module({
  imports: [UsersModule, LeaderboardModule, SaveFilesModule],
  controllers: [AppController],
  providers: [AppService, PrismaService],
})
export class AppModule {}
