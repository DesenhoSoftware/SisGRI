package org.sisgri.worship


import java.text.SimpleDateFormat
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.sisgri.people.NaturalPerson

@Secured(['ROLE_ADMIN', 'ROLE_SECRETARY'])
@Transactional(readOnly = true)
class WorshipController {

    def springSecurityService
    def jasperService
    def personService
    def dateService
    def churchService
    def static worships = []

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def search() {
        respond new Worship(params), model:[churchList: churchService.churchList()]
    }

    def resultSearch() {
        dateService.setDates(params)

        def criteria = Worship.createCriteria()
        worships = criteria.list {
            church{
                if(params.church!=""){
                    eq("name", params.church)
                }
            }
            if(params.rulingName!="")
                like("rulingName", "%"+params.rulingName+"%")
            if(params.prelectorName!="") 
                like("prelectorName", "%"+params.prelectorName+"%")
            if(params.date_year != "" || (params.date_year != "" && params.date_month != ""))
                between('date', dateService.dateBefore, dateService.dateAfter)
            else if(params.date_year == "" && params.date_month != "")
                sqlRestriction "extract( month from date ) = "+params.date_month

            if(params.type!="")
                eq("type", params.type)

            order("date", "desc")
        }

        respond worships
    }
    
    def print() {
        def parameters = [churchName: springSecurityService.currentUser.person.church.name]

        def reportDef = new JasperReportDef(name:'worshipList.jrxml',
            fileFormat:JasperExportFormat.PDF_FORMAT, reportData: worships,
            parameters: parameters)

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "filename=lista_cultos.pdf")
        response.outputStream << jasperService.generateReport(reportDef).toByteArray()
    }

    def show(Worship worshipInstance) {
        respond worshipInstance
    }

    def create() {
        respond new Worship(params), model:[churchList: churchService.churchList()]
    }

    @Transactional
    def save(Worship worshipInstance) {
        if (worshipInstance == null) {
            notFound()
            return
        }

        setRulingAndPrelector(worshipInstance, params)
        
        if (worshipInstance.hasErrors()) {
            respond worshipInstance.errors, view:'create'
            return
        }

        worshipInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'worshipInstance.label', default: 'Worship'), worshipInstance.id])
                redirect worshipInstance
            }
            '*' { respond worshipInstance, [status: CREATED] }
        }
    }

    protected def setRulingAndPrelector(Worship worshipInstance, def params) {
        worshipInstance.ruling = NaturalPerson.get(params.rulingID)
        worshipInstance.prelector = NaturalPerson.get(params.prelectorID)

        if (!worshipInstance.ruling)
            worshipInstance.rulingName = params.rulingName
        else
            worshipInstance.rulingName = worshipInstance.ruling.name

        if (!worshipInstance.prelector)
            worshipInstance.prelectorName = params.prelectorName
        else
            worshipInstance.prelectorName = worshipInstance.prelector.name
    }

    def edit(Worship worshipInstance) {
        respond worshipInstance, model:[churchList: churchService.churchList()]
    }

    @Transactional
    def update(Worship worshipInstance) {
        if (worshipInstance == null) {
            notFound()
            return
        }

        setRulingAndPrelector(worshipInstance, params)

        if (worshipInstance.hasErrors()) {
            respond worshipInstance.errors, view:'edit'
            return
        }

        worshipInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Worship.label', default: 'Worship'), worshipInstance.id])
                redirect worshipInstance
            }
            '*'{ respond worshipInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Worship worshipInstance) {

        if (worshipInstance == null) {
            notFound()
            return
        }

        worshipInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Worship.label', default: 'Worship'), worshipInstance.id])
                redirect action:"search", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def choosePerson() {
        String name = params.person
        
        render template: "people", model: [people: personService.searchNaturalPeople(name)]
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'worshipInstance.label', default: 'Worship'), params.id])
                redirect action: "search", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
