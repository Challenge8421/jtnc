/**
 * The BSD 3-Clause License ("BSD New" or "BSD Simplified")
 *
 * Copyright © 2015 Trust HS Bremen and its Contributors. All rights   
 * reserved.
 *
 * See the CONTRIBUTORS file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ietf.nea.pa.attribute.enums;

/**
 * Enumeration of known integrity measurement attribute types.
 *
 *
 */
public enum PaAttributeTypeEnum {

    /**
     * 0 - Testing Reserved - for use in specification examples,
     * experimentation, and testing.
     *
     */
    IETF_PA_TESTING(0),
    /**
     * 1 - Attribute Request - Contains a list of attribute type values defining
     * the attributes desired from the Posture Collectors.
     *
     */
    IETF_PA_ATTRIBUTE_REQUEST(1),
    /**
     * 2 - Product Information - Manufacturer and product information for the
     * component.
     *
     */
    IETF_PA_PRODUCT_INFORMATION(2),
    /**
     * 3 - Numeric Version - Numeric version of the component.
     *
     */
    IETF_PA_NUMERIC_VERSION(3),
    /**
     * 4 - Version String - version of the component.
     *
     */
    IETF_PA_STRING_VERSION(4),
    /**
     * 5 - Operational Status - Describes whether the component is running on
     * the endpoint.
     *
     */
    IETF_PA_OPERATIONAL_STATUS(5),
    /**
     * 6 - Port Filter - Lists the set of ports (e.g., TCP port 80 for HTTP),
     * that are allowed or blocked on the endpoint.
     *
     */
    IETF_PA_PORT_FILTER(6),
    /**
     * 7 - Installed Packages - List of software packages installed on an
     * endpoint that provides the requested component.
     */
    IETF_PA_INSTALLED_PACKAGES(7),
    /**
     * 8 - PA-TNC Error - PA-TNC message or attribute processing error.
     *
     */
    IETF_PA_ERROR(8),
    /**
     * 9 - Assessment Result - Result of the assessment performed by a Posture
     * Validator.
     */
    IETF_PA_ASSESSMENT_RESULT(9),
    /**
     * 10 - Remediation Instructions - Instructions for remediation generated by
     * a Posture Validator.
     */
    IETF_PA_REMEDIATION_INSTRUCTIONS(10),
    /**
     * 11 - Forwarding Enabled - Indicates whether packet forwarding has been
     * enabled between different interfaces on the endpoint.
     */
    IETF_PA_FORWARDING_ENABLED(11),
    /**
     * 12 - Factory Default Password - Indicates whether the endpoint Enabled
     * has a factory default password enabled.
     *
     */
    IETF_PA_FACTORY_DEFAULT_PW_ENABLED(12),
    /**
     * Reserved - for attribute routing.
     */
    IETF_PA_RESERVED(0xffffffffL);

    private long id;

    /**
     * Creates the type with the given type ID.
     *
     * @param id the type ID
     */
    private PaAttributeTypeEnum(final long id) {
        this.id = id;
    }

    /**
     * Returns the message type ID.
     *
     * @return the type ID
     */
    public long id() {
        return this.id;
    }

    /**
     * Returns the type for the given type ID.
     *
     * @param id the type ID
     * @return a type or null
     */
    public static PaAttributeTypeEnum fromId(final long id) {

        if (id == IETF_PA_TESTING.id) {
            return IETF_PA_TESTING;
        }

        if (id == IETF_PA_ATTRIBUTE_REQUEST.id) {
            return IETF_PA_ATTRIBUTE_REQUEST;
        }

        if (id == IETF_PA_PRODUCT_INFORMATION.id) {
            return IETF_PA_PRODUCT_INFORMATION;
        }

        if (id == IETF_PA_NUMERIC_VERSION.id) {
            return IETF_PA_NUMERIC_VERSION;
        }

        if (id == IETF_PA_STRING_VERSION.id) {
            return IETF_PA_STRING_VERSION;
        }

        if (id == IETF_PA_OPERATIONAL_STATUS.id) {
            return IETF_PA_OPERATIONAL_STATUS;
        }

        if (id == IETF_PA_PORT_FILTER.id) {
            return IETF_PA_PORT_FILTER;
        }

        if (id == IETF_PA_INSTALLED_PACKAGES.id) {
            return IETF_PA_INSTALLED_PACKAGES;
        }

        if (id == IETF_PA_ERROR.id) {
            return IETF_PA_ERROR;
        }

        if (id == IETF_PA_ASSESSMENT_RESULT.id) {
            return IETF_PA_ASSESSMENT_RESULT;
        }

        if (id == IETF_PA_REMEDIATION_INSTRUCTIONS.id) {
            return IETF_PA_REMEDIATION_INSTRUCTIONS;
        }

        if (id == IETF_PA_FORWARDING_ENABLED.id) {
            return IETF_PA_FORWARDING_ENABLED;
        }

        if (id == IETF_PA_FACTORY_DEFAULT_PW_ENABLED.id) {
            return IETF_PA_FACTORY_DEFAULT_PW_ENABLED;
        }

        if (id == IETF_PA_RESERVED.id) {
            return IETF_PA_RESERVED;
        }

        return null;
    }

}
