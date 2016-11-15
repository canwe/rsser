<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<#macro page loginBox=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">
<html lang="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>RSSer</title>

	<!-- Framework CSS -->
	<link rel="stylesheet" href="/css/blueprint/screen.css" type="text/css" media="screen, projection"/>
	<link rel="stylesheet" href="/css/blueprint/print.css" type="text/css" media="print"/>    
	<!--[if IE]><link rel="stylesheet" href="/css/blueprint/ie.css" type="text/css" media="screen, projection"/><![endif]-->

	<link rel="stylesheet" href="/css/screen.css" type="text/css" media="screen, projection"/>
	
	<script type="text/javascript" src="/js/jquery-1.2.3.pack.js"></script>
	<script type="text/javascript" src="/js/rsser.js"><!-- nothing --></script>
</head>	
<body>
	<div class="container">  
	    <h1><a href="/index.html">RSS reader - bundle RSS on-line</a></h1>
	    <hr />
		
		<div class="span-16 colborder">
			<#nested />
		</div>
		
		<div class="span-7 last">
			<#if RequestParameters.loginFailure?? && RequestParameters.loginFailure = '1'>
				<div class="box error">Invalid username or password</div> 
			</#if>
		
			<div class="box">
				<@security.authorize ifAnyGranted="ROLE_USER">
					<p>
						Welcome, <strong><@security.authentication property="principal.username"/></strong>
					</p>

					<ul class="bottom">
						<li>
                     <a href="/unread.html">New entries (${unreadEntries})</a>
						</li>
						<li>
							<a href="/feeds.html">View subscriptions</a>
						</li>
					</ul>
					
					<br />
					
					<ul class="bottom">
						<li>
							<a href="/logout.html">Logout</a>
						</li>
					</ul>
				</@security.authorize>
			
				<@security.authorize ifNotGranted="ROLE_USER">
					<p>
						<strong>Sign in to access your account:</strong>
					</p>
					<p>
						<form method="post" action="/login.html">
							<label for="login">Login:</label>
							<br />
							<input type="text" class="text login" id="login" name="j_username" value="" />
							<br />
							<label for="password">Password:</label>
							<br />
							<input type="password" class="text login" id="password" name="j_password" value="" />
							<br />
							<input type="submit" value="Sign In" class="text submit" />
						</form>
					</p>
					<ul class="bottom">
						<li>
							<a href="/register.html">Sign Up</a>
						</li>
						<li>
							<a href="/password.html">Forgot password?</a>
						</li>
					</ul>
				</@security.authorize>
			</div>
		</div>
	</div>
</body>
</html>
</#macro>

<#macro entry_row entry index readEntries = []>
	<#if index % 2 = 0>
		<tr class="even">
		<#else>
		<tr class="odd">
		</#if>
			<td class="feedRow">
				<div class="read">
					<label for="feed${entry.id}">
						<#if readEntries?? && readEntries?seq_contains(entry.id)>
							<input type="checkbox" id="feed${entry.id}" class="entry" value="${entry.key}" checked="checked" />
						<#else>
							<input type="checkbox" id="feed${entry.id}" class="entry" value="${entry.key}" />
						</#if>
						Read
					</label>
				</div>
				<h6><a href="${entry.url}">${entry.titleString}</a></h6>
				<h5>${entry.dateString}</h5>
				<p>
					${entry.descriptionString}
				</p>
			</td>
		</tr>
</#macro>
