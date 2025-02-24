DROP TABLE IF EXISTS "public"."ntr_fickle_entry";
CREATE TABLE "public"."ntr_fickle_entry"
(
  "id"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "fickle_entry1"     INT4,
  "fickle_entry2"     INT8,
  "fickle_entry3"     FLOAT4,
  "fickle_entry4"     FLOAT8,
  "fickle_entry5"     VARCHAR(256) COLLATE "pg_catalog"."default",
  "fickle_entry6"     TEXT,
  "fickle_entry7"     TIMESTAMPTZ,
  "time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_FICKLE_ENTRY" PRIMARY KEY ("id")
);

CREATE INDEX "IDX_NTR_FICKLE_ENTRY_TIME" ON "public"."ntr_fickle_entry" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

DROP TABLE IF EXISTS "public"."ntr_fickle_key";
CREATE TABLE "public"."ntr_fickle_key"
(
    "id"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
    "description" VARCHAR(256) COLLATE "pg_catalog"."default",
    "fickle_key1"     INT4,
    "fickle_key2"     INT8,
    "fickle_key3"     FLOAT4,
    "fickle_key4"     FLOAT8,
    "fickle_key5"     VARCHAR(256) COLLATE "pg_catalog"."default",
    "fickle_key6"     TEXT,
    "fickle_key7"     TIMESTAMPTZ,
    "time" TIMESTAMPTZ,
    CONSTRAINT "PK_NTR_CF_FICKLE_KEY" PRIMARY KEY ("id")
);

CREATE INDEX "IDX_NTR_FICKLE_KEY_TIME" ON "public"."ntr_fickle_key" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

DROP TABLE IF EXISTS "public"."ntr_fickle_value";
CREATE TABLE "public"."ntr_fickle_value"
(
    "id"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
    "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
    "description" VARCHAR(256) COLLATE "pg_catalog"."default",
    "fickle_value1"     INT4,
    "fickle_value2"     INT8,
    "fickle_value3"     FLOAT4,
    "fickle_value4"     FLOAT8,
    "fickle_value5"     VARCHAR(256) COLLATE "pg_catalog"."default",
    "fickle_value6"     TEXT,
    "fickle_value7"     TIMESTAMPTZ,
    "time" TIMESTAMPTZ,
    CONSTRAINT "PK_NTR_CF_FICKLE_VALUE" PRIMARY KEY ("id")
);

CREATE INDEX "IDX_NTR_FICKLE_VALUE_TIME" ON "public"."ntr_fickle_value" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);