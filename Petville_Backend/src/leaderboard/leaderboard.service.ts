import { Injectable } from '@nestjs/common';
import { PrismaService } from '../prisma.service';
import { CreateLeaderboardDto } from './dto/create-leaderboard.dto';
import { UpdateLeaderboardDto } from './dto/update-leaderboard.dto';

@Injectable()
export class LeaderboardService {
  constructor(private prisma: PrismaService) {}

  create(createLeaderboardDto: CreateLeaderboardDto) {
    return this.prisma.leaderboard.create({ data: createLeaderboardDto });
  }

  findAll() {
    return this.prisma.leaderboard.findMany({
      include: {
        save_file: {
          select: {
            petType: true,
          },
        },
        user: {
          select: {
            name: true,
          },
        },
      },
    });
  }

  findOne(id: number) {
    return this.prisma.leaderboard.findUnique({ where: { id } });
  }

  update(id: number, updateLeaderboardDto: UpdateLeaderboardDto) {
    return this.prisma.leaderboard.update({ where: { id }, data: updateLeaderboardDto });
  }

  remove(id: number) {
    return this.prisma.leaderboard.delete({ where: { id } });
  }
}