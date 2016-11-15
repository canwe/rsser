<#include "/elements.ftl" />

<@page>
	<div class="span-16">
		<h2>Subscribe</h2>
		
		<@form.form commandName="feed" action="/subscribe.html">
			<p>
				<label for="login">URL</label><br />
				<@form.input path="feedUrl" cssClass="text" cssErrorClass="text error" /><br />
				<@form.errors path="*" element="span" cssClass="errorText" />
			</p>
			
			<p>
				<input type="submit" value="Subscribe" class="text submit" />
			</p>
		</@form.form>
	</div>
</@page>