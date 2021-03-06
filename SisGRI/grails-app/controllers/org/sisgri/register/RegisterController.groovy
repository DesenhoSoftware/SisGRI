package org.sisgri.register

import org.sisgri.people.Person
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

@Secured(['ROLE_ADMIN', 'ROLE_TREASURER'])
@Transactional(readOnly = true)
class RegisterController {

    def springSecurityService
    def personService
    def jasperService
    def registerService
    def churchService
    def static registers = []

    static allowedMethods = [save: "POST", delete: "DELETE"]

    def resultSearch() {
        registerService.setRestrictDates(params)

        Date from = new Date()
        Date to = new Date()
        from = from.parse('dd/MM/yyyy', params.from)
        to = to.parse('dd/MM/yyyy', params.to)
        
        def criteria = Register.createCriteria()
        registers = criteria.list {
            church{
                if(params.church!=""){
                    eq("name", params.church[0])
                }
            }

            between('date', from, to)

            if (params.search == "Ver registros") {
                eq("type", params.type)

                if(params.name!="")
                    like("name", "%"+params.name+"%")
                if(params.entryRegister!="")
                    eq("entryRegister", params.entryRegister)
                if(params.exitRegister!="")
                    eq("exitRegister", params.exitRegister)
                if(params.value!="") {
                    Double value = params.value.replace(",",".").toDouble()
                    eq("value", value)
                }
            }

            order("date", "asc")
        }

        registerService.setTotal(registers)
        registerService.setParameters(params)
        
        if (params.search == "Ver registros") {
            respond registers, model: [typeRegister: params.type]
        }
        else {
            redirect action: "cityArticle", params: params
        }
    }

    def search() {
        respond new Register(params), model:[churchList: churchService.churchList()]
    }

    def cityArticle() {
        registerService.setCityArticle(params, registers)

        respond params
    }

    def print() {
        def reportDef = new JasperReportDef(name:'registerList.jrxml',
            fileFormat:JasperExportFormat.PDF_FORMAT, reportData: registers,
            parameters: registerService.parameters)

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=lista_registros.pdf")
        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
    }

    def printWorkers() {
        def data = []
        registers.each {
            data << [date: it.date, name: it.name, formatedValue: it.formatedValue, post: it.person?.post]
        }

        def reportDef = new JasperReportDef(name:'registerWorkers.jrxml',
            fileFormat:JasperExportFormat.PDF_FORMAT, reportData: data,
            parameters: registerService.parameters)

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=lista_registros.pdf")
        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
    }

    def printCityArticle() {
        def period = params.month + "/" + params.year
        def parameters = [churchName: springSecurityService.currentUser.person.church.name, period: period] + params

        def reportDef = new JasperReportDef(name:'cityArticle.jrxml',
            fileFormat:JasperExportFormat.PDF_FORMAT, reportData: [registers], parameters: parameters)

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=boletim.pdf")
        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
    }

    def printEntry(Register registerInstance) {
        def data = [registerInstance]
        def parameters = [churchName: springSecurityService.currentUser.person.church.name]

        def reportDef = new JasperReportDef(name:'entry_register.jrxml',
            fileFormat:JasperExportFormat.PDF_FORMAT, reportData: data,
            parameters: parameters)

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=registro_entrada.pdf")
        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
    }

    def printExity(Register registerInstance) {
        def data = [registerInstance]

        def cpf = registerInstance.person?.cpf ? registerInstance.person.cpf : ""

        def parameters = [churchName: springSecurityService.currentUser.person.church.name,
            exitRegister: registerInstance.exitRegister.substring(7), cpf: cpf]

        def reportDef = new JasperReportDef(name:'exit_register.jrxml',
            fileFormat:JasperExportFormat.PDF_FORMAT, reportData: data,
            parameters: parameters)

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=registro_saida.pdf")
        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
    }

    def show(Register registerInstance) {
        respond registerInstance
    }

    def create() {
        respond new Register(params), model:[churchList: churchService.churchList()]
    }

    def choosePerson() {
        String name = params.personName
        
        render template: "people", model: [people: personService.searchPeople(name)]
    }

    protected def setPerson(Register registerInstance, def params) {
        registerInstance.person = Person.get(params.personID)

        if (!registerInstance.person)
            registerInstance.name = params.name
        else
            registerInstance.name = registerInstance.person.name
    }

    @Transactional
    def save(Register registerInstance) {
        if (registerInstance == null) {
            notFound()
            return
        }

        if (params.name != "") {
            setPerson(registerInstance, params)
        }

        if (registerInstance.hasErrors()) {
            respond registerInstance.errors, view:'create', model:[churchList: churchService.churchList()]
            return
        }

        registerInstance.save flush:true
        registerService.calculateTransferAndBalance(registerInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'registerInstance.label', default: 'Register'), registerInstance.id])
                redirect registerInstance
            }
            '*' { respond registerInstance, [status: CREATED] }
        }
    }

    @Transactional
    def delete(Register registerInstance) {

        if (registerInstance == null) {
            notFound()
            return
        }

        registerService.deleteRegister(registerInstance)
        registerInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Register.label', default: 'Register'), registerInstance.id])
                redirect action:"search", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'registerInstance.label', default: 'Register'), params.id])
                redirect action: "search", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
