cf enable-feature-flag diego_docker
cf push wordpress-docker --docker-image library/wordpress
open http://wordpress-docker.apps.gcp.plausible.co
