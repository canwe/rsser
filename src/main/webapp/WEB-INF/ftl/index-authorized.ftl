<#include "/elements.ftl" />

<@page>
	<div class="span-16">
		<h2>Subscribe</h2>
		
		<form method="post" action="/subscribe.html">
			<p>
				<label for="login">URL</label><br />
				<input name="feedUrl" class="text" value="" />
			</p>
			
			<p>
				<input type="submit" value="Subscribe" class="text submit" />
			</p>
		</form>
	</div>
</@page>