DROP TABLE IF EXISTS "public"."ntr_common";
CREATE TABLE "public"."ntr_common"
(
  "id"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_COMMON" PRIMARY KEY ("id")
);

CREATE INDEX "IDX_NTR_COMMON_TIME" ON "public"."ntr_common" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);