/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UpaaS;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author Roman Pineda
 */
@WebService(serviceName = "KpiUpload")
@Stateless()
public class KpiUpload {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "upload")
    public String Upload(@WebParam(name = "KPI") int kpi,@WebParam(name = "Description") String description) {
        return "Success";
    }


}
