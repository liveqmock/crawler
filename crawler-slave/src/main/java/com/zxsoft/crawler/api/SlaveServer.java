package com.zxsoft.crawler.api;

import java.util.List;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zxsoft.crawler.api.JobStatus.State;
import com.zxsoft.crawler.slave.SlavePath;
import com.zxsoft.crawler.slave.utils.DbService;

public class SlaveServer {
        private static final Logger LOG = LoggerFactory.getLogger(SlaveServer.class);

        private Component component;
        private SlaveApp app;
        private int port;
        private boolean running;

        private static boolean enableGetNetworkSearchTaskFromDb = false;

        public static boolean enableGetNetworkSearchTaskFromDb() {
                return enableGetNetworkSearchTaskFromDb;
        }

        public SlaveServer(int port) {
                this.port = port;
                // Create a new Component.
                component = new Component();

                // Add a new HTTP server listening on port 8182.
                component.getServers().add(Protocol.HTTP, port);

                // Attach the application.
                app = new SlaveApp();

                component.getDefaultHost().attach("/" + SlavePath.PATH, app);

                component.getContext().getParameters().set("maxThreads", "1000");

                SlaveApp.server = this;
        }

        public boolean isRunning() {
                return running;
        }

        public void start() throws Exception {
                LOG.info("Starting SlaveNode on port " + port + "...");
                component.start();
                LOG.info("Started SlaveNode on port " + port);
                running = true;
                SlaveApp.started = System.currentTimeMillis();

                if (enableGetNetworkSearchTaskFromDb) {
                        DbService dbService = new DbService();
                        dbService.updateExecuteTaskStatus();
                }
        }

        public boolean canStop() throws Exception {
                List<JobStatus> jobs = SlaveApp.jobMgr.list(null, State.RUNNING);
                if (!jobs.isEmpty()) {
                        return false;
                }
                return true;
        }

        public boolean stop(boolean force) throws Exception {
                if (!running) {
                        return true;
                }
                if (!canStop() && !force) {
                        LOG.warn("Running jobs - can't stop now.");
                        return false;
                }
                LOG.info("Stopping NutchServer on port " + port + "...");
                component.stop();
                LOG.info("Stopped NutchServer on port " + port);
                running = false;
                return true;
        }

        public static void main(String[] args) throws Exception {
                if (args.length == 0) {
                        System.err.println("Usage: CrawlerServer <port> [enableSearchTask]");
                        System.exit(-1);
                }
                if (args.length == 2) {
                        if ("enableSearchTask".equals(args[1])) {
                                enableGetNetworkSearchTaskFromDb = true;
                        }
                }
                int port = Integer.parseInt(args[0]);
                SlaveServer server = new SlaveServer(port);
                server.start();
        }
}
