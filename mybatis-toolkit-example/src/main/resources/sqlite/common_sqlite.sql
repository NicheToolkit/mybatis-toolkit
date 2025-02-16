DROP TABLE IF EXISTS `ntr_common`;
CREATE TABLE IF NOT EXISTS `ntr_common`
(
  `id`          VARCHAR(64) NOT NULL,
  `name`        VARCHAR(128),
  `description` VARCHAR(256),
  `time` TIMESTAMP,
  CONSTRAINT `PK_NTR_CF_COMMON` PRIMARY KEY (`id`)
);

CREATE INDEX `IDX_NTR_COMMON_TIME` ON `ntr_common` (`time`);
