<#include "/elements.ftl" />

<@page>
	<h2>Sign Up</h2>
	
	<@form.form commandName="user">
		<p>
			<label for="login">Login</label><br />
			<@form.input path="login" cssClass="text" cssErrorClass="text error" /><br />
			<@form.errors path="login" element="span" cssClass="errorText" />
		</p>
		
		<p>
			<label for="password">Password</label><br />
			<@form.password path="password" cssClass="text" cssErrorClass="text error" /><br />
			<@form.errors path="password" element="span" cssClass="errorText" />
		</p>

		<p>
			<label for="confirmPassword">Confirm password</label><br />
			<@form.password path="confirmPassword" cssClass="text" cssErrorClass="text error" /><br />
			<@form.errors path="confirmPassword" element="span" cssClass="errorText" />
		</p>

		<p>
			<label for="email">E-mail</label><br />
			<@form.input path="email" cssClass="text" cssErrorClass="text error" /><br />
			<@form.errors path="email" element="span" cssClass="errorText" />
		</p>
		
		<p>
			<input type="submit" value="Sign Up" class="text submit" />
		</p>
	</@form.form>
</@page>
