DESCRIPTION="kairos-configs"
PN="kairos-configs"
LICENSE="CLOSED"
SRC_URI = "git://github.com/kairos-io/packages.git;protocol=https;branch=main"
SRCREV = "main"
# Disable url checksum
BB_STRICT_CHECKSUM = "0"
S = "${WORKDIR}/git"
FILES_${PN} += "/system/oem/*"

# We dont compile anything
do_compile() {
    :
}

do_install() {
    install -d ${D}/system/oem
    cp -r ${S}/packages/static/kairos-overlay-files/files/system/oem/* ${D}/system/oem/

}