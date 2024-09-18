DROP TABLE IF EXISTS "public"."ntr_template";
CREATE TABLE "public"."ntr_template"
(
  "template_pk1"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "template_pk2"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "time" TIMESTAMPTZ,
  "logic_sign" INT4,
  "create_time" TIMESTAMPTZ,
  "update_time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_TEMPLATE" PRIMARY KEY ("template_pk1","template_pk2","logic_sign")
);

CREATE INDEX "IDX_NTR_TEMPLATE_TEMPLATE_PK1" ON "public"."ntr_template" USING btree (
  "template_pk1" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_TEMPLATE_PK2" ON "public"."ntr_template" USING btree (
  "template_pk2" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_TIME" ON "public"."ntr_template" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_LOGIC_SIGN" ON "public"."ntr_template" USING btree (
  "logic_sign" "pg_catalog"."int4_ops" ASC NULLS LAST
);

DROP TABLE IF EXISTS "public"."ntr_template_dynamic";
CREATE TABLE "public"."ntr_template_dynamic"
(
  "template_pk1"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "template_pk2"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "time" TIMESTAMPTZ,
  "logic_sign" INT4,
  "create_time" TIMESTAMPTZ,
  "update_time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_TEMPLATE_DYNAMIC" PRIMARY KEY ("template_pk1","template_pk2","logic_sign")
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK1" ON "public"."ntr_template_dynamic" USING btree (
  "template_pk1" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK2" ON "public"."ntr_template_dynamic" USING btree (
  "template_pk2" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_TIME" ON "public"."ntr_template_dynamic" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_LOGIC_SIGN" ON "public"."ntr_template_dynamic" USING btree (
  "logic_sign" "pg_catalog"."int4_ops" ASC NULLS LAST
);