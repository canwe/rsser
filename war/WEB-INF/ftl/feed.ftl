<#include "/elements.ftl" />

<@page>
	<h2>Entries from channel - ${feed.titleString}</h2>

	<div class="read">
		<label for="readAll">
			<input type="checkbox" id="readAll" value="${feed.key}" />
			Mark all as read
		</label>
	</div>
	
	<input type="hidden" id="idFeed" value="${feed.key}" />
	
	<div class="clean"></div>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<#list feedEntries as entry>
			<@entry_row entry entry_index readEntries />
		</#list>
	</table>
</@page>