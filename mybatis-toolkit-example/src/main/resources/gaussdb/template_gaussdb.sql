DROP TABLE IF EXISTS "public"."ntr_template";
CREATE TABLE "public"."ntr_template"
(
  "template_pk1"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "template_pk2"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "link_id1"     VARCHAR(64) COLLATE "pg_catalog"."default",
  "link_id2"     VARCHAR(64) COLLATE "pg_catalog"."default",
  "time" TIMESTAMPTZ,
  "status1" INT4,
  "status2" INT4,
  "operate" INT4,
  "logic" INT4,
  "create_time" TIMESTAMPTZ,
  "update_time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_TEMPLATE" PRIMARY KEY ("template_pk1","template_pk2")
);

CREATE INDEX "IDX_NTR_TEMPLATE_TEMPLATE_PK1" ON "public"."ntr_template" USING btree (
  "template_pk1" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_TEMPLATE_PK2" ON "public"."ntr_template" USING btree (
  "template_pk2" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_LINK_ID1" ON "public"."ntr_template" USING btree (
  "link_id1" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_LINK_ID2" ON "public"."ntr_template" USING btree (
  "link_id2" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_TIME" ON "public"."ntr_template" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_OPERATE" ON "public"."ntr_template" USING btree (
  "operate" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_STATUS1" ON "public"."ntr_template" USING btree (
  "status1" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_STATUS2" ON "public"."ntr_template" USING btree (
  "status2" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_LOGIC" ON "public"."ntr_template" USING btree (
  "logic" "pg_catalog"."int4_ops" ASC NULLS LAST
);

DROP TABLE IF EXISTS "public"."ntr_template_dynamic";
CREATE TABLE "public"."ntr_template_dynamic"
(
  "template_pk1"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "template_pk2"          VARCHAR(64) COLLATE "pg_catalog"."default" NOT NULL,
  "name"        VARCHAR(128) COLLATE "pg_catalog"."default",
  "description" VARCHAR(256) COLLATE "pg_catalog"."default",
  "link_id1"     VARCHAR(64) COLLATE "pg_catalog"."default",
  "link_id2"     VARCHAR(64) COLLATE "pg_catalog"."default",
  "time" TIMESTAMPTZ,
  "status1" INT4,
  "status2" INT4,
  "operate" INT4,
  "logic" INT4,
  "create_time" TIMESTAMPTZ,
  "update_time" TIMESTAMPTZ,
  CONSTRAINT "PK_NTR_CF_TEMPLATE_DYNAMIC" PRIMARY KEY ("template_pk1","template_pk2")
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK1" ON "public"."ntr_template_dynamic" USING btree (
  "template_pk1" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_TEMPLATE_PK2" ON "public"."ntr_template_dynamic" USING btree (
  "template_pk2" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_LINK_ID1" ON "public"."ntr_template_dynamic" USING btree (
  "link_id1" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_LINK_ID2" ON "public"."ntr_template_dynamic" USING btree (
  "link_id2" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_TIME" ON "public"."ntr_template_dynamic" USING btree (
  "time" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_STATUS1" ON "public"."ntr_template_dynamic" USING btree (
  "status1" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_STATUS2" ON "public"."ntr_template_dynamic" USING btree (
  "status2" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_OPERATE" ON "public"."ntr_template_dynamic" USING btree (
  "operate" "pg_catalog"."int4_ops" ASC NULLS LAST
);

CREATE INDEX "IDX_NTR_TEMPLATE_DYNAMIC_LOGIC" ON "public"."ntr_template_dynamic" USING btree (
  "logic" "pg_catalog"."int4_ops" ASC NULLS LAST
);