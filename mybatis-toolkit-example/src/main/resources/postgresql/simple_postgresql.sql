DROP TABLE IF EXISTS "public"."ntr_simple";
CREATE TABLE "public"."ntr_simple"
(
  "id"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "link_id"     VARCHAR(64) COLLATE "pg_catalog"."default",
  "time" TIMESTAMPTZ,
  "status" INT4,
  "operate" INT4,
  "logic" INT4,
  "create_time" TIMESTAMPTZ,
  "update_time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_SIMPLE" PRIMARY KEY ("id")
);

CREATE INDEX "IDX_NTR_SIMPLE_LINK_ID" ON "public"."ntr_simple" USING btree (
  "link_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_TIME" ON "public"."ntr_simple" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_STATUS" ON "public"."ntr_simple" USING btree (
  "status" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_OPERATE" ON "public"."ntr_simple" USING btree (
  "operate" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_LOGIC" ON "public"."ntr_simple" USING btree (
  "logic" "pg_catalog"."int4_ops" ASC NULLS LAST
);

DROP TABLE IF EXISTS "public"."ntr_simple_dynamic";
CREATE TABLE "public"."ntr_simple_dynamic"
(
  "id"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "link_id"     VARCHAR(64) COLLATE "pg_catalog"."default",
  "time" TIMESTAMPTZ,
  "status" INT4,
  "operate" INT4,
  "logic" INT4,
  "create_time" TIMESTAMPTZ,
  "update_time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_SIMPLE_DYNAMIC" PRIMARY KEY ("id")
);
CREATE INDEX "IDX_NTR_SIMPLE_DYNAMIC_LINK_ID" ON "public"."ntr_simple_dynamic" USING btree (
  "link_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_DYNAMIC_TIME" ON "public"."ntr_simple_dynamic" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_DYNAMIC_STATUS" ON "public"."ntr_simple_dynamic" USING btree (
  "status" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_DYNAMIC_OPERATE" ON "public"."ntr_simple_dynamic" USING btree (
  "operate" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_SIMPLE_DYNAMIC_LOGIC" ON "public"."ntr_simple_dynamic" USING btree (
  "logic" "pg_catalog"."int4_ops" ASC NULLS LAST
);