package controllers.src;

/**
 * @author Alex Brown
 */

/**
 * Facade for project
 */
public class ProjectFACADE {
    private UserList userList;
    private ProjectList projectList;

    /**
     * Contrustor cretes new user list and project list
     */
    public ProjectFACADE() {
        this.userList = UserList.getUserList();
        this.projectList = ProjectList.getProjectList();
    }

    /**
     * Gets project currently being worked on
     * 
     * @return current project
     */
    public Project getCurrentProject() {
        return projectList.currentProject;
    }

    /**
     * Logs in user
     * 
     * @param username
     * @param password
     * @return login status
     */
    public boolean login(String username, String password) {
        return userList.login(username, password);
    }

    /**
     * Registers new user
     * 
     * @param username
     * @param password
     * @param email
     * @return regerster status
     */

    public boolean register(String username, String password, String email) {
        return userList.register(password, username, email);
    }

    /**
     * logs user out
     * 
     * @return logout status
     */
    public boolean logout() {
        return userList.logout();
    }

    /**
     * gets project
     * 
     * @param title
     * @return project
     */
    public Project getProject(String title) {
        return projectList.openProject(title);
    }

    /**
     * opens project
     * 
     * @param title
     * @return project
     */
    public Project openProject(String title) {
        return projectList.openProject(title);
    }

    /**
     * Makes new project
     * 
     * @param title
     * @return If project is created
     */
    public boolean createProject(String title) {
        return projectList.createProject(userList.user, title);
    }

    /**
     * Deletes project
     * 
     * @param project
     * @return If project is deleted
     */
    public boolean deleteProject(Project project) {
        return projectList.deleteProject(project);
    }

    /**
     * Creates section
     * 
     * @param title
     * @return Section
     */
    public Section createSection(String title) {
        Section section = new Section(title);
        projectList.currentProject.sections.add(section);
        return section;
    }

    /**
     * Gets section based on title
     * 
     * @param title
     * @return Section
     */
    public Section getSection(String title) {
        for (Section section : projectList.currentProject.sections) {
            if (section.title.equals(title))
                return section;
        }
        return null;
    }

    /**
     * deletes a section
     * 
     * @param project
     * @param section
     * @return If section is delted
     */
    public boolean deleteSection(Project project, Section section) {
        return project.deleteSection(section);
    }

    /**
     * creates a task
     * 
     * @param sectionTitle
     * @param title
     * @param description
     * @param priority
     * @param type
     * @return Task
     */
    public Task createTask(String sectionTitle, String title, String description, int priority, String type) {
        Task task = new Task(title, description, priority, type);
        projectList.currentProject.getSection(sectionTitle).createTask(task);
        return task;
    }

    /**
     * creates a task
     * 
     * @param section
     * @param title
     * @param description
     * @param priority
     * @param type
     * @return If task is created
     */
    public Task createTask(Section section, String title, String description, int priority, String type) {
        return section.createTask(new Task(title, description, priority, type));
    }

    /**
     * moves task
     * 
     * @param taskTitle
     * @param sectionTitle
     * @return
     */
    public boolean moveTask(String taskTitle, String sectionTitle) {
        Section oldSection = null;
        Task movingTask = null;
        for (Section section : projectList.currentProject.sections) {
            for (Task task : section.tasks) {
                if (task.title.equals(taskTitle)) {
                    oldSection = section;
                    movingTask = task;
                }
            }
        }

        if (oldSection == null)
            return false;
        oldSection.tasks.remove(movingTask);
        getSection(sectionTitle).createTask(movingTask);

        return true;
    }

    /**
     * Deletes task
     * 
     * @param section
     * @param task
     * @return if task is deleted
     */
    public boolean deleteTask(Section section, Task task) {
        return section.deleteTask(task);
    }

    /**
     * Moves task
     * 
     * @param currentSection
     * @param nextSection
     * @param task
     * @return if task is moved
     */
    public boolean moveTask(Section currentSection, Section nextSection, Task task) {
        currentSection.deleteTask(task);
        nextSection.createTask(task);
        return true;
    }

    /**
     * creates a comment on project
     * 
     * @param project
     * @param content
     * @param user
     * @return CreateCommentStatus
     */
    public boolean createComment(Project project, String content, User user) {
        return project.createComment(new Comment(content, user));
    }

    /**
     * creates comment on task
     * 
     * @param task
     * @param content
     * @return CreateCommentStatus
     */
    public boolean createComment(Task task, String content) {
        System.out.println(userList.user);
        return task.createComment(new Comment(content, userList.user));
    }

    /**
     * create comment on comment
     * 
     * @param comment
     * @param content
     * @return CreateCommentStatus
     */
    public boolean createComment(Comment comment, String content) {
        return comment.createComment(new Comment(content, userList.user));
    }

    /**
     * deletes comment on project
     * 
     * @param project
     * @param comment
     * @return if comment is deleted
     */
    public boolean deleteComment(Project project, Comment comment) {
        return project.deleteComment(comment);
    }

    /**
     * deletes comment on task
     * 
     * @param task
     * @param comment
     * @return if comment is delted
     */
    public boolean deleteComment(Task task, Comment comment) {
        return task.deleteComment(comment);
    }

    /**
     * deletes comment on coment
     * 
     * @param rootComment
     * @param comment
     * @return if comment is deleted
     */
    public boolean deleteComment(Comment rootComment, Comment comment) {
        return rootComment.deleteComment(comment);
    }

    /**
     * adds user to project
     * 
     * @param user
     * @param project
     * @return if user is added
     */
    public boolean addUserToProject(User user, Project project) {
        return project.addUser(user);
    }

    /**
     * adds user to task
     * 
     * @param task
     * @param user
     * @return if user is added
     */
    public boolean addAssignedUser(Task task, User user) {
        return task.addAssignedUser(user);
    }

    /**
     * removes user from task
     * 
     * @param task
     * @param user
     * @return if user is removed
     */
    public boolean removeAssignedUser(Task task, User user) {
        return task.removeAssignedUser(user);
    }

    /**
     * saves data
     * 
     * @return if data is saved
     */
    public boolean saveData() {
        boolean userRes = DataWriter.saveUsers(userList.users);
        boolean projectRes = DataWriter.saveProjects(projectList.projects);
        if (userRes && projectRes)
            return true;
        else
            return false;
    }

    public Task getTask(String title) {
        for (Section section : projectList.currentProject.sections) {
            for (Task task : section.tasks) {
                if (task.title.equals(title))
                    return task;
            }
        }

        return null;
    }

    public Task moveTask(Task targetTask, String sectionTitle) {
        Section removeSection = null;
        Section addSection = null;
        for (Section section : projectList.currentProject.sections) {
            if (section.title.equals(sectionTitle)) {
                addSection = section;
            }
            for (Task task : section.tasks) {
                if (task.id.equals(targetTask.id)) {
                    removeSection = section;
                }
            }
        }
        removeSection.tasks.remove(targetTask);
        return addSection.createTask(targetTask);
    }

    public User getUser() {
        return userList.user;
    }

    public User getUser(String username) {
        for (User user : userList.users) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public Comment getComment(Task targetTask, String content) {
        for (Comment comment : targetTask.comments) {
            if (comment.content.equals(content))
                return comment;
        }

        return null;
    }

}
