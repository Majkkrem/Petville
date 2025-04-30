-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2025. Ápr 30. 23:17
-- Kiszolgáló verziója: 10.4.32-MariaDB
-- PHP verzió: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `petville`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `inventory`
--

CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `currentFood` int(11) NOT NULL,
  `currentHeal` int(11) NOT NULL,
  `currentEnergyBar` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `inventory`
--

INSERT INTO `inventory` (`id`, `user_id`, `currentFood`, `currentHeal`, `currentEnergyBar`) VALUES
(1, 1, 1, 1, 1),
(2, 1, 10, 5, 2);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `leaderboard`
--

CREATE TABLE `leaderboard` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `save_files`
--

CREATE TABLE `save_files` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `petName` varchar(191) NOT NULL,
  `petType` varchar(191) NOT NULL,
  `petEnergy` int(11) NOT NULL,
  `petHunger` int(11) NOT NULL,
  `petMood` int(11) NOT NULL,
  `petHealth` int(11) NOT NULL,
  `hoursPlayer` int(11) NOT NULL,
  `goldEarned` int(11) NOT NULL,
  `currentGold` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `save_files`
--

INSERT INTO `save_files` (`id`, `user_id`, `petName`, `petType`, `petEnergy`, `petHunger`, `petMood`, `petHealth`, `hoursPlayer`, `goldEarned`, `currentGold`) VALUES
(1, 1, 'Fluffy', 'Cat', 85, 30, 90, 95, 50, 2500, 1200);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(191) NOT NULL,
  `email` varchar(191) NOT NULL,
  `password` varchar(191) NOT NULL,
  `dateOfRegister` datetime(3) NOT NULL DEFAULT current_timestamp(3),
  `role` enum('ADMIN','USER') NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `dateOfRegister`, `role`) VALUES
(1, 'testuser1', 'test@gmail.com', '$argon2id$v=19$m=65536,t=3,p=4$J/t2lUZuRHQeIDu8j6Sw4g$o2EVtNDYoBI82oj+HF3QjHB6vouJl/MpbuRl1zBiN0Y', '2025-04-11 09:10:47.938', 'USER'),
(2, 'pleasework1', 'legyszi@test.com', '$argon2id$v=19$m=65536,t=3,p=4$3FJ3QzotQmAg9eFrkznmBQ$Rhi28WFopdu5WJ2XqU5H3SrQDudP2OdPMUKr8LvCxmw', '2025-04-28 13:04:06.227', 'USER');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Inventory_user_id_fkey` (`user_id`);

--
-- A tábla indexei `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Leaderboard_user_id_fkey` (`user_id`);

--
-- A tábla indexei `save_files`
--
ALTER TABLE `save_files`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Save_files_user_id_fkey` (`user_id`);

--
-- A tábla indexei `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Users_name_key` (`name`),
  ADD UNIQUE KEY `Users_email_key` (`email`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `inventory`
--
ALTER TABLE `inventory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `leaderboard`
--
ALTER TABLE `leaderboard`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `save_files`
--
ALTER TABLE `save_files`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT a táblához `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `Inventory_user_id_fkey` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE;

--
-- Megkötések a táblához `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD CONSTRAINT `Leaderboard_user_id_fkey` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE;

--
-- Megkötések a táblához `save_files`
--
ALTER TABLE `save_files`
  ADD CONSTRAINT `Save_files_id_fkey` FOREIGN KEY (`id`) REFERENCES `inventory` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `Save_files_user_id_fkey` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
