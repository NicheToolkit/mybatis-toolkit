* default mapper

<table style="text-align: center;">
<tr>
<th>name</th>
<th>typeParams</th>
<th>methods</th>
<th>description</th>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisAlertMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, S, I&gt;</code></td>
<td><code>alertById(@Param("id") I id, @Param("status") S status)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>alertById</code>、<code>alertAll</code> and <code>alertAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>alertDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertAll(@Param("idList") Collection&lt;I&gt; idList, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertAllByWhere(@Param("whereSql") String whereSql, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("status") S status)</code></td>
</tr>    
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisDeleteMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, I&gt;</code></td>
<td><code>deleteById(@Param("id") I id)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>deleteById</code>、 <code>deleteAll</code> and <code>deleteAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>deleteDynamicById(@Param("tablename") String tablename, @Param("id") I id)</code></td>
</tr>
<tr>
<td><code>deleteAll(@Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>deleteDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>deleteAllByWhere(@Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td><code>deleteDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisOperateMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, I&gt;</code></td>
<td><code>operateById(@Param("id") I id, @Param("operate") Integer operate)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>operate</code> and <code>operateAll</code> methods.</td>
</tr>
<tr>
<td><code>operateDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateAll(@Param("idList") Collection&lt;I&gt; idList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateAllByWhere(@Param("whereSql") String whereSql, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisRemoveMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>removeById(@Param("id") I id, @Param("logic") String logic)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>removeById</code> 、<code>removeAll</code> and <code>removeAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>removeDynamicById(@Param("tablename") String tablename, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeAll(@Param("idList") Collection&lt;I&gt; idList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeAllByWhere(@Param("whereSql") String whereSql, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql,@Param("logic") String logic)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisSaveMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>save(@Param("entity") E entity)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>save</code> and <code>saveAll</code> methods.</td>
</tr>
<tr>
<td><code>saveDynamic(@Param("tablename") String tablename, @Param("entity") E entity)</code></td>
</tr>
<tr>
<td><code>saveAll(@Param("entityList") Collection&lt;E&gt; entityList)</code></td>
</tr>
<tr>
<td><code>saveDynamicAll(@Param("tablename") String tablename, @Param("entityList") Collection&lt;E&gt; entityList)</code></td>
</tr>
<tr>
<td rowspan=6 style="vertical-align: middle;"><code>MybatisFindMapper</code></td>
<td rowspan=6 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>findById(@Param("id") I id)</code></td>
<td rowspan=6 style="vertical-align: middle;">the mapper is used to handle <code>find</code>、 <code>findAll</code> and <code>findAllByWhere</code> methods.</td>
</tr>
<tr>
<td><code>findDynamicById(@Param("tablename") String tablename, @Param("id") I id)</code></td>
</tr>
<tr>
<td><code>findAll(@Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>findDynamicAll(@Param("tablename") String tablename, @Param("idList") Collection&lt;I&gt; idList)</code></td>
</tr>
<tr>
<td><code>findAllByWhere(@Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td><code>findDynamicAllByWhere(@Param("tablename") String tablename, @Param("whereSql") String whereSql)</code></td>
</tr>
<tr>
<td style="vertical-align: middle;"><code>MybatisSuperMapper</code></td>
<td style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td></td>
<td style="vertical-align: middle;">the mapper is default super mapper.</td>
</tr>
<tr>
<td rowspan=8 style="vertical-align: middle;"><code>MybatisInfoMapper</code></td>
<td rowspan=8 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,I&gt;</code></td>
<td><code>findByName(@Param("name") String name, @Param("logic") String logic)</code></td>
<td rowspan=8 style="vertical-align: middle;">the mapper is used to handle <code>find</code> and <code>findAll</code> methods.</td>
</tr>
<tr>
<td><code>findDynamicByName(@Param("tablename") String tablename, @Param("name") String name, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findByNameAndNotId(@Param("name") String name, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findDynamicByNameAndNotId(@Param("tablename") String tablename, @Param("name") String name, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findByEntity(@Param("entity") E entity, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findDynamicByEntity(@Param("tablename") String tablename, @Param("entity") E entity, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findByEntityAndNotId(@Param("entity") E entity, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>findDynamicByEntityAndNotId(@Param("tablename") String tablename, @Param("entity") E entity, @Param("id") I id, @Param("logic") String logic)</code></td>
</tr>
</table>

* link mapper

<table style="text-align: center;">
<tr>
<th>name</th>
<th>typeParams</th>
<th>methods</th>
<th>description</th>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisDeleteLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, L, I&gt;</code></td>
<td><code>deleteByLinkId(@Param("linkId") L linkId)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>deleteByLinkId</code> and <code>deleteAllByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>deleteDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId)</code></td>
</tr>
<tr>
<td><code>deleteAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList)</code></td>
</tr>
<tr>
<td><code>deleteDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisOperateLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, L, I&gt;</code></td>
<td><code>operateByLinkId(@Param("linkId") L linkId, @Param("operate") Integer operate)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>operateByLinkId</code> and <code>operateAllByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>operateDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td><code>operateDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("operate") Integer operate)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisRemoveLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;,L,I&gt;</code></td>
<td><code>removeByLinkId(@Param("linkId") L linkId, @Param("logic") String logic)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>removeByLinkId</code> and <code>removeAllByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>removeDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td><code>removeDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("logic") String logic)</code></td>
</tr>
<tr>
<td rowspan=4 style="vertical-align: middle;"><code>MybatisAlertLinkMapper</code></td>
<td rowspan=4 style="vertical-align: middle;"><code>&lt;E extends RestId&lt;I&gt;, L, S, I&gt;</code></td>
<td><code>alertByLinkId(@Param("linkId") L linkId, @Param("status") S status)</code></td>
<td rowspan=4 style="vertical-align: middle;">the mapper is used to handle <code>alertByLinkId</code> and <code>alertByLinkIds</code> methods.</td>
</tr>
<tr>
<td><code>alertDynamicByLinkId(@Param("tablename") String tablename, @Param("linkId") L linkId,  @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertAllByLinkIds(@Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("status") S status)</code></td>
</tr>
<tr>
<td><code>alertDynamicAllByLinkIds(@Param("tablename") String tablename, @Param("linkIdList") Collection&lt;L&gt; linkIdList, @Param("status") S status)</code></td>
</tr>
</table>

* simple mapper examples

```java

@Mapper
public interface SimpleMapper extends MybatisInfoMapper<SimpleEntity, String>,
        MybatisAlertLinkMapper<SimpleEntity, String, Integer, String>,
        MybatisDeleteLinkMapper<SimpleEntity, String, String>,
        MybatisRemoveLinkMapper<SimpleEntity, String, String>,
        MybatisOperateLinkMapper<SimpleEntity, String, String> {
}
```

* template mapper examples

```java

@Mapper
public interface TemplateMapper extends MybatisInfoMapper<TemplateEntity, TemplateIdentity>,
        MybatisAlertLinkMapper<TemplateEntity, TemplateLinkage, TemplateAlertness, TemplateIdentity>,
        MybatisDeleteLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisRemoveLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity>,
        MybatisOperateLinkMapper<TemplateEntity, TemplateLinkage, TemplateIdentity> {
}
```