package org.netkernelroc.gradle.util

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
/**
 * Class provides helper methods for working with NetKernel
 *
 * TODO: Decide how to handle responses that could begin with file://
 */

class NetKernelHelper {
    def isNetKernelRunning() {
        def retValue = false

        try {
            def http = new HTTPBuilder('http://localhost:1060')
            http.uri.path = "/"

            http.request(Method.GET) {
                response.success = { resp, reader ->
                    assert resp.status == 200
                    retValue = true
                }
            }
        } catch (Throwable t) {
        }

        retValue
    }

    /**
     * Returns a NetKernel property value by sending a remote script query to the NetKernel sandbox.
     *
     * NB: This will be replaced with a call to a  proper REST interface after we finish working on all the
     * interactions with NetKernel.
     *
     * @param propertyReference
     * @throws Exception
     */
    def queryNetKernelProperty(String propertyReference) throws Exception {
        def String encodedProperty = java.net.URLEncoder.encode(propertyReference)
        def queryURL = 'http://localhost:1060/tools/scriptplaypen?action2=execute&type=gy&example&identifier&name&space&script=context%2EcreateResponseFrom%28context%2Esource%28%22'+encodedProperty+'%22%29%29'
        def String queryResponse = new URL(queryURL).text
        return queryResponse
    }

    def whereIsNetKernelInstalled() throws Exception {

        def installLocation = queryNetKernelProperty('netkernel:/config/netkernel.install.path').substring(5)

        return installLocation
    }

    def whereIsModuleExtensionDirectory() throws Exception {

        def extensionDirectoryRelativeLocation = queryNetKernelProperty('netkernel:/config/netkernel.init.modulesdir')

        return extensionDirectoryRelativeLocation
    }

}
