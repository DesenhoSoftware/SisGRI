<%@ page import="org.sisgri.church.Headquarter" %>

<div class="form-group">
	<div class="fieldcontain ${hasErrors(bean: headquarterInstance, field: 'name', 'error')} required">
		<label class="control-label col-md-3" for="name">
			<g:message code="headquarter.name.label" default="Nome" />
			<span class="required-indicator">*</span>
		</label>
		<div class="col-md-4">
			<g:textField class="form-control" name="name" required="" value="${headquarterInstance?.name}"/>
		</div>
	</div>
</div>


<div class="form-group">
	<div class="fieldcontain ${hasErrors(bean: headquarterInstance, field: 'address', 'error')} required">
		<label class="control-label col-md-3" for="address">
			<g:message code="headquarter.address.label" default="Endereço" />
			<span class="required-indicator">*</span>
		</label>
		<div class="col-md-4">
			<g:textField class="form-control" name="address" required="" value="${headquarterInstance?.address}"/>
		</div>
	</div>
</div>

