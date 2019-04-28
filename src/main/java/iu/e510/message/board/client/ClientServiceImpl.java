package iu.e510.message.board.client;

import iu.e510.message.board.db.model.DMBPost;
import iu.e510.message.board.server.ClientAPI;
import iu.e510.message.board.util.Config;
import iu.e510.message.board.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

public class ClientServiceImpl implements ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private Config config;
    private ClientAPI clientAPI;
    private Registry registry;
    private List<String> superNodeList;
    private String clientID;

    public ClientServiceImpl(String clientID) {
        this.clientID = clientID;
        this.config = new Config();
        String RMI_HOST = config.getConfig(Constants.RMI_REGISTRY_HOST);
        int RMI_PORT = Integer.parseInt(config.getConfig(Constants.RMI_REGISTRY_PORT));
        this.superNodeList = getSuperNodeList();
        try {
            registry = LocateRegistry.getRegistry(RMI_HOST, RMI_PORT);
            clientAPI = getWorkingClientAPI();
        } catch (RemoteException e) {
            logger.error("Error occurred while getting the registry!");
        }
    }

    @Override
    public boolean post(String topic, String title, String content) {
        try {
            return clientAPI.post(clientID, topic, title, content);
        } catch (RemoteException e) {
           serverConnectionRefresh();
        }
        return true;
    }

    @Override
    public void upvotePost(String topic, int postID) {
        try {
            clientAPI.upvotePost(clientID, topic, postID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downvotePost(String topic, int postID) {
        try {
            clientAPI.downvotePost(clientID, topic, postID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void replyPost(String topic, int postID, String content) {
        try {
            clientAPI.replyPost("temp", topic, postID, content);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upvoteReply(String topic, int postID, int replyID) {
        try {
            clientAPI.upvoteReply(clientID, topic, postID, replyID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downvoteReply(String topic, int postID, int replyID) {
        try {
            clientAPI.downvoteReply(clientID, topic, postID, replyID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DMBPost getPost(String topic, int postID) {
        try {
            return clientAPI.getPost(clientID, topic, postID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DMBPost> getPosts(String topic) {
        try {
            return clientAPI.getPosts(clientID, topic);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a working Supernode RMI object from the provided Super node list.
     * @return
     */
    private ClientAPI getWorkingClientAPI() {
        ClientAPI clientAPI;
        for (String superNode : superNodeList) {
            logger.info("Trying to connect to: " + superNode);
            try {
                clientAPI = (ClientAPI) registry.lookup(superNode);
                logger.info("Connected to Super Node: " + superNode);
                return clientAPI;
            } catch (Exception e) {
                logger.info(superNode + " cannot be reached. Hence trying the next node");
            }
        }
        throw new RuntimeException("Cannot connect to any of the supernodes. Please verify the list");
    }

    /**
     * If a server is not reachable, then updates and points to a working server in the given list.
     */
    private void serverConnectionRefresh() {
        logger.error("Error occurred trying to access the Super Node! Please retry the command");
        getWorkingClientAPI();
    }

    /**
     * Reads the list of super nodes from the configs and return an ArrayList
     * @return
     */
    private List<String> getSuperNodeList() {
        String superNodes = this.config.getConfig(Constants.SUPER_NODE_LIST);
        return Arrays.asList(superNodes.split(","));
    }
}
