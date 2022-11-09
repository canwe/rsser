<#include "/elements.ftl" />

<@page>
	<h2>Unread entries</h2>

	<#if entries?size &gt; 0>
		<table border="0" cellspacing="0" cellpadding="0">
			<#list entries as entry>
				<@entry_row entry entry_index />
			</#list>
		</table>
	<#else>
		No unread messages
	</#if>
</@page>