Go into `apps_proc/poky`
Clone this repo into meta-kairos `git clone https://github.com/Itxaka/meta-kairos.git meta-kairos`

Apply meta-qti-ubuntu patch inside the meta-qti-ubuntu dir to make sure we install kairos-agent/config and deps in the final image and lso enable root ssh login.
`cd apps_proc/poky/meta-qti-ubuntu && patch -p1 < ../meta-kairos/meta-qti-ubuntu.patch`

Build as normal. Final image should contain the kairos-agent, kairos configsand needed deps.

ssh login as kairos/kairos and sudo to elevate privs.
