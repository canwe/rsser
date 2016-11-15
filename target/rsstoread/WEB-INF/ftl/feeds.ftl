<#include "/elements.ftl" />

<@page>
	<h2>You are subscribed to the following channels</h2>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th class="span-4">Title</th>
			<th class="span-4">Description</th>
			<th class="span-6">URL</th>
			<th class="span-2"></th>
		</tr>
		
		<#list feeds as feed>
			<#if feed_index % 2 != 0>
			<tr class="even">
			<#else>
			<tr class="odd">
			</#if>
				<td>${feed.titleString}</td>
				<td>${feed.descriptionString}</td>
				<td>${feed.url}</td>
				<td><a href="/feed.html?feedId=${feed.key}">Show</a></td>
			</tr>
		</#list>
	</table>
</@page>