
<%@ page import="org.sisgri.worship.Worship" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'worship.label', default: 'Worship')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row">
	        <div class="col-md-12">
	            <h3 class="page-title">Worship</h3>
	            <ul class="page-breadcrumb breadcrumb">
	                <li>
	                    <i class="fa fa-home"></i>
	                    <a href="${createLink(uri: '/')}">
							Página Inicial
						</a>
	                    <i class="fa fa-angle-right"></i>
	                </li>
	                <li>
	                	<g:link>
							Worships
						</g:link>
						<i class="fa fa-angle-right"></i>
	                </li>
	                <li>
						Detalhar
	                </li>
	            </ul>
	        </div>
	    	<div class="col-md-12">
				<g:if test="${flash.message}">
					<div class="alert alert-info alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
					    ${flash.message}
					</div>
				</g:if>
			</div>
			<div class="col-md-12">
				<div class="portlet box blue">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-reorder"></i>Detalhar Worship
						</div>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<g:form class="form-horizontal" role="form">
							<div class="form-body">
								<h3 class="form-section">Dados</h3>
								<div class="row">
									
									<g:if test="${worshipInstance?.church}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">Church</label>
												
											<div class="col-md-9">
												<p class="form-control-static">
													<g:link controller="church" action="show" id="${worshipInstance?.church?.id}">${worshipInstance?.church?.encodeAsHTML()}</g:link>
												</p>
											</div>
										
											</div>
										</div>
									</g:if>
									
									<g:if test="${worshipInstance?.type}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">Type</label>
												
											<div class="col-md-9">
												<p class="form-control-static">
													<g:fieldValue bean="${worshipInstance}" field="type"/>
												</p>
											</div>
											
											</div>
										</div>
									</g:if>
									
									<g:if test="${worshipInstance?.date}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">Date</label>
												
												<div class="col-md-9">
													<p class="form-control-static">
														<g:formatDate date="${worshipInstance?.date}" />
													</p>
												</div>
											
											</div>
										</div>
									</g:if>
									
									<g:if test="${worshipInstance?.ruling}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">Ruling</label>
												
											<div class="col-md-9">
												<p class="form-control-static">
													<g:link controller="person" action="show" id="${worshipInstance?.ruling?.id}">${worshipInstance?.ruling?.encodeAsHTML()}</g:link>
												</p>
											</div>
										
											</div>
										</div>
									</g:if>
									
									<g:if test="${worshipInstance?.prelector}">
										<div class="col-md-6">
											<div class="form-group">
												<label class="control-label col-md-3">Prelector</label>
												
											<div class="col-md-9">
												<p class="form-control-static">
													<g:link controller="person" action="show" id="${worshipInstance?.prelector?.id}">${worshipInstance?.prelector?.encodeAsHTML()}</g:link>
												</p>
											</div>
										
											</div>
										</div>
									</g:if>
									
									</div>
								</div>
							<div class="form-actions right">
								<div class="row">
									<div class="col-md-12">
										<div class="col-md-offset-3 col-md-9">
											<g:hiddenField name="id" value="${worshipInstance?.id}" />

											<g:link class="btn blue" action="edit" id="${worshipInstance?.id}">
											<i class="fa fa-pencil"></i> Editar	</g:link>

											<g:actionSubmit class="btn red" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Você tem certeza?')}');">
											</g:actionSubmit>
										</div>
									</div>
								</div>
							</div>
						</g:form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
