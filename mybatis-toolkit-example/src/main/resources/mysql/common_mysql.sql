DROP TABLE IF EXISTS `ntr_common`;
CREATE TABLE IF NOT EXISTS `ntr_common`
(
    `id`          VARCHAR(64) NOT NULL,
    `name`        VARCHAR(128),
    `description` VARCHAR(256),
    `time` TIMESTAMP,
    CONSTRAINT `PK_NTR_COMMON` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_common` ADD INDEX IDX_NTR_COMMON_TIME (`time`);
