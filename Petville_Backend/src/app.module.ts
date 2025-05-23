import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { UsersModule } from './users/users.module';
import { LeaderboardModule } from './leaderboard/leaderboard.module';
import { SaveFilesModule } from './save_files/save_files.module';
import { PrismaService } from './prisma.service';
import { AuthModule } from './auth/auth.module';
import { ConfigModule } from '@nestjs/config';
import { InventoriesModule } from './inventories/inventories.module';
import { LoadModule } from './load/load.module';

@Module({

  imports: [UsersModule, LeaderboardModule, SaveFilesModule, AuthModule,
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    InventoriesModule,
    LoadModule
  ],
  controllers: [AppController],
  providers: [AppService, PrismaService],
})
export class AppModule {}
