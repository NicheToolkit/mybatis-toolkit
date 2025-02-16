DROP TABLE IF EXISTS `ntr_fickle_entry`;
CREATE TABLE IF NOT EXISTS `ntr_fickle_entry`
(
    `id`          VARCHAR(64) NOT NULL,
    `name`        VARCHAR(128),
    `description` VARCHAR(256),
    `fickle_entry1`     INT,
    `fickle_entry2`     LONG,
    `fickle_entry3`     FLOAT,
    `fickle_entry4`     DOUBLE,
    `fickle_entry5`     VARCHAR(256),
    `fickle_entry6`     TEXT,
    `fickle_entry7`     TIMESTAMP,
    `time` TIMESTAMP,
    CONSTRAINT `PK_NTR_FICKLE_ENTRY` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_fickle_entry` ADD INDEX IDX_NTR_FICKLE_ENTRY_TIME (`time`);


DROP TABLE IF EXISTS `ntr_fickle_key`;
CREATE TABLE IF NOT EXISTS `ntr_fickle_key`
(
    `id`          VARCHAR(64) NOT NULL,
    `name`        VARCHAR(128),
    `description` VARCHAR(256),
    `fickle_key1`     INT,
    `fickle_key2`     LONG,
    `fickle_key3`     FLOAT,
    `fickle_key4`     DOUBLE,
    `fickle_key5`     VARCHAR(256),
    `fickle_key6`     TEXT,
    `fickle_key7`     TIMESTAMP,
    `time` TIMESTAMP,
    CONSTRAINT `PK_NTR_FICKLE_KEY` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_fickle_key` ADD INDEX IDX_NTR_FICKLE_KEY_TIME (`time`);


DROP TABLE IF EXISTS `ntr_fickle_value`;
CREATE TABLE IF NOT EXISTS `ntr_fickle_value`
(
    `id`          VARCHAR(64) NOT NULL,
    `name`        VARCHAR(128),
    `description` VARCHAR(256),
    `fickle_value1`     INT,
    `fickle_value2`     LONG,
    `fickle_value3`     FLOAT,
    `fickle_value4`     DOUBLE,
    `fickle_value5`     VARCHAR(256),
    `fickle_value6`     TEXT,
    `fickle_value7`     TIMESTAMP,
    `time` TIMESTAMP,
    CONSTRAINT `PK_NTR_FICKLE_VALUE` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `ntr_fickle_value` ADD INDEX IDX_NTR_FICKLE_VALUE_TIME (`time`);
