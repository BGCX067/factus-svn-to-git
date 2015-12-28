call install-artifact rome-fetcher-0.9.jar rome rome-fetcher 0.9
rem call install-artifact rome-0.9.jar rome rome 0.9

call mvn install:install-file -DgroupId=com.sun.jmx -DartifactId=jmxri -Dversion=1.2.1 -Dpackaging=jar -Dfile=jmxri.jar
call mvn install:install-file -DgroupId=com.sun.jdmk -DartifactId=jmxtools -Dversion=1.2.1 -Dpackaging=jar -Dfile=jmxtools.jar
call mvn install:install-file -DgroupId=javax.jms -DartifactId=jms -Dversion=1.1 -Dpackaging=jar -Dfile=jms.jar

rem solr
call install-artifact solr/apache-solr-solrj-1.3-dev.jar org.apache.solr solrj 1.3-dev
call install-artifact solr/apache-solr-common-1.3-dev.jar org.apache.solr solr-commons 1.3-dev
