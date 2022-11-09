<#include "/elements.ftl" />

<@page>
	<h2>Users</h2>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th class="span-2">ID</th>
			<th class="span-4">Login</th>
			<th class="span-4">Password</th>
			<th class="span-6">E-mail</th>
		</tr>
		
		<#list users as user>
			<#if user_index % 2 != 0>
			<tr class="even">
			<#else>
			<tr class="odd">
			</#if>
				<td>${user.id}</td>
				<td>${user.login}</td>
				<td>${user.password}</td>
				<td>${user.email}</td>
			</tr>
		</#list>
	</table>
</@page>