<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'congregation.label', default: 'Congregation')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-congregation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

		<div class="row">
	        <div class="col-md-12">
	            <h3 class="page-title">Congregation</h3>
	            <ul class="page-breadcrumb breadcrumb">
	            	<li>
	                    <i class="fa fa-home"></i>
	                    <a href="${createLink(uri: '/')}">
									Página Inicial
						</a>
	                    <i class="fa fa-angle-right"></i>
	                </li>
	                <li>
	                	<g:link action="index">
							Congregation
						</g:link>
						<i class="fa fa-angle-right"></i>
	                </li>
	                <li>
	                    <g:link  action="create">
							Criar
						</g:link>
	                </li>
	            </ul>
	            <!-- END PAGE TITLE & BREADCRUMB-->
	        </div>


		<div id="create-congregation" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			
			<div class="row">
		    	<div class="col-md-12">
					<g:if test="${flash.message}">
						<div class="alert alert-info alert-dismissable">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						    ${flash.message}
						</div>
					</g:if>
				</div>
			</div>
			
			
			%{-- AKI --}%
			<div class="row">
				<div class="col-md-12">
					<g:hasErrors bean="${congregationInstance}">
						<ul class="errors" role="alert">
							<g:eachError bean="${congregationInstance}" var="error">
								<div class="alert alert-danger alert-dismissable">
									<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
									<g:if test="${error in org.springframework.validation.FieldError}">
										"${error.field}"
									</g:if>>
									<g:message error="${error}"/>
								</div>
							</g:eachError>
						</ul>
					</g:hasErrors>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<div class="portlet box blue">
						<div class="portlet-body form">
							<div class="portlet box blue">
							</div>
							<g:form url="[resource:congregationInstance, action:'save']"  class="form-horizontal">
								<div class="form-body">
									<h3 class="form-section"><small>Crie um novo Congregation!</small></h3>
									<div class="alert alert-danger display-hide">
										<button class="close" data-close="alert"></button>
										O formulário contêm erros. Por favor verifique abaixo.
									</div>
									<g:render template="form"/>
								</div>
								<div class="form-actions right">
									<div class="col-md-offset-3 col-md-9">
										<button type="submit" class="btn blue">Submeter</button>
									</div>
								</div>
							</g:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
