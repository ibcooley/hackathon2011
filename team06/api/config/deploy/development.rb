# Application Naming
#
set :application, "simplevent"
set :scm, "git"
set :branch, "master"
set :user, "jmayhak"
set :repository,  "git@github.com:Jmayhak/bounce-house-supreme-hacks.git"

# Application Deployment Location
#

set :deploy_to, "/srv/www/simplevent.yliapps.com/#{application}"
set :document_root, "/srv/www/simplevent.yliapps.com/"
# set :deploy_via, :remote_cache
set :copy_exclude, [".git", ".DS_Store", ".idea"]
set :use_sudo, false
set :ssh_options, {:forward_agent => true}
set :keep_releases, 10

role :app, "173.230.128.84"
role :web, "173.230.128.84"
role :db,  "173.230.128.84", :primary => true

namespace :deploy do

		task :update do
			transaction do
				symlink
				migrate
			end
		end

		task :finalize_update do
			transaction do
				run "chmod -R g+w #{releases_path}/#{release_name}"
			end
		end

		task :symlink do
			transaction do
				run "ln -nfs #{current_release} #{deploy_to}/#{current_dir}"
			end
		end

		task :migrate do
			transaction do
				run "cd #{deploy_to}/#{current_dir}/api/db && php main.php db:migrate ENV=development"
			end
		end

end
